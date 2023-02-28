package com.example.demo.controllers;

import com.example.demo.dao.UsuarioDao;
import com.example.demo.models.Usuario;
import com.example.demo.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//return "Vayomer Elohim yeji-or vayeji-or.";// dijo D-os: Haya luz, y hubo luz.
@RestController
public class UsuarioController {
    @Autowired
    private UsuarioDao usuarioDao;
    private JWTUtil jwtUtil;


    //método para listar los usuarios de la BD en la Página de users.html
    @RequestMapping(value = "api/users", method = RequestMethod.GET)
    public List<Usuario> getUsuarios(@RequestHeader(value="Authorization") String token) {
        if (!validarToken(token)) {
            return null;
        }

        return usuarioDao.getUsuarios();
    }

    private boolean validarToken(String token) {
        String usuarioId = jwtUtil.getKey(token);
        return usuarioId != null;
    }

    @RequestMapping(value = "api/users", method = RequestMethod.POST)
    //RequestBody es para que el objeto Json que se recibe se convierta en un objeto de java
    public void registrarUsuario(@RequestBody Usuario usuario) {
        //Con Argon2 se encripta la contraseña
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(2, 65536, 1, usuario.getPassword());
        usuario.setPassword(hash);

        usuarioDao.regUsuario(usuario);//
    }

    @RequestMapping(value = "api/del_user/{id}", method = RequestMethod.DELETE)
    public void delUsuario(@RequestHeader(value = "Authorization") String token,
                           @PathVariable Long id) {
        if (!validarToken(token)) {
            return;
        }
        usuarioDao.delete(id);
    }


}
