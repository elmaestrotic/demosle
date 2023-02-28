package com.example.demo.dao;

import com.example.demo.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class UsuarioDaoImp implements UsuarioDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Usuario> getUsuarios() {
        //consulta con hibernate usamos nombre d la clase no d la tabla
        String hql = "FROM Usuario";
        return entityManager.createQuery(hql).getResultList();
    }

    @Override
    public void regUsuario(Usuario usuario) {

        entityManager.merge(usuario);
    }

    @Override
    public Usuario verificarCredenciales(Usuario usuario) {
        String hql = "FROM Usuario where email = :email";
        List<Usuario> lista = entityManager.createQuery(hql)
                .setParameter("email", usuario.getEmail())
                .getResultList();
//controlamos si la lista esta vacia para evitar NullPointerException
        if (lista.isEmpty()) {
            return null;
        }

        //Validar contraseña obtenida del hash de la base de datos
        String passwordHashed = lista.get(0).getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if (argon2.verify(lista.get(0).getPassword(), usuario.getPassword())) {
            return lista.get(0);//devolvemos el primer user de la lista indice 0
        } else {
            return null;
        }

    }

    @Override
    public void delete(Long id) {
        Usuario usuario = entityManager.find(Usuario.class, id);
        entityManager.remove(usuario);
    }

}
