package com.example.demo.controllers;

import com.example.demo.dao.UsuarioDao;
import com.example.demo.models.Usuario;
import com.example.demo.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    //RequestBody es para que el objeto Json que se recibe se convierta en un objeto de java
    public String login(@RequestBody Usuario usuario) {
        Usuario userLogueado = usuarioDao.verificarCredenciales(usuario);
        //obtener usuario por credenciales
        if (userLogueado != null) {
            String tokenJWT = jwtUtil.create(String.valueOf(userLogueado.getId()), userLogueado.getEmail());
            return tokenJWT;
        }
        return "FAIL";
        // return "Usuario no autenticado\n verifique sus credenciales e intente nuevamente";
    }

}
