package com.unisul.eventos.repository;

import com.unisul.eventos.model.Usuario;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class UsuarioRepository {

    private static final String FILE = "usuarios.data";

    /** Lê todos os usuários do arquivo. */
    public static List<Usuario> loadAll() {
        List<Usuario> usuarios = new ArrayList<>();
        Path path = Paths.get(FILE);
        if (!Files.exists(path)) return usuarios;

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    usuarios.add(Usuario.fromCsv(line));
                } catch (Exception e) {
                    System.err.println("Linha inválida ignorada em " + FILE + ": " + line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro lendo " + FILE, e);
        }
        return usuarios;
    }

    /** Salva todos os usuários no arquivo (sobrescreve). */
    public static void saveAll(List<Usuario> usuarios) {
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(FILE))) {
            for (Usuario u : usuarios) {
                bw.write(u.toCsv());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro salvando " + FILE, e);
        }
    }
}