package br.com.anibal.armory.simpleserver.services;

import br.com.anibal.armory.simpleserver.entities.Curso;
import br.com.anibal.armory.simpleserver.exceptions.ConflictException;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class CursoService {

    private final String COLLECTION = "cursos";

    public Optional<Curso> getCurso(String id) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentSnapshot> future = getDBCollection().document(id).get();
        DocumentSnapshot snapshot = future.get();

        Optional<Curso> optional = Optional.ofNullable(snapshot.toObject(Curso.class));
        optional.ifPresent(curso -> curso.setId(snapshot.getId()));
        return optional;
    }

    private CollectionReference getDBCollection() {
        return FirestoreClient.getFirestore().collection(COLLECTION);
    }

    public Curso criarCurso(Curso curso) throws ConflictException, ExecutionException, InterruptedException {
        verifyUnique(curso);

        ApiFuture<DocumentReference> created = getDBCollection().add(curso);

        String id = created.get().getId();
        curso.setId(id);

        return curso;
    }

    private void verifyUnique(Curso curso) throws ExecutionException, InterruptedException, ConflictException {
        ApiFuture<QuerySnapshot> future = getDBCollection().whereEqualTo("nome", curso.getNome()).get();

        QuerySnapshot query = future.get();

        if (!query.isEmpty()) {
            throw new ConflictException();
        }
    }

    public List<Curso> getCursos() throws ExecutionException, InterruptedException {

        ApiFuture<QuerySnapshot> future = getDBCollection().get();

        QuerySnapshot query = future.get();

        return query.getDocuments().stream()
                .map(document -> {
                    Curso curso = document.toObject(Curso.class);
                    curso.setId(document.getId());
                    return curso;
                })
                .collect(Collectors.toList());
    }

    public Boolean deleteCurso(String id) throws ExecutionException, InterruptedException {
        DocumentReference reference = getDBCollection().document(id);
        ApiFuture<DocumentSnapshot> document = reference.get();

        if (document.get().exists()) {
            reference.delete();
            return true;
        }

        return false;
    }

    public Optional<Curso> atualizarCurso(String id, Curso curso) throws ExecutionException, InterruptedException {
        DocumentReference reference = getDBCollection().document(id);
        ApiFuture<DocumentSnapshot> document = reference.get();

        if (document.get().exists()) {
            reference.set(curso);
            return Optional.of(curso);
        } else {
            return Optional.empty();
        }
    }

}
