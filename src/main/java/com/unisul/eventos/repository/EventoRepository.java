package com.unisul.eventos.repository;

import com.unisul.eventos.model.Evento;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class EventoRepository {

    private static final String FILE = "events.data";

    public static List<Evento> loadAll() {
        List<Evento> eventos = new ArrayList<>();
        Path path = Paths.get(FILE);
        if (!Files.exists(path)) return eventos;

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    eventos.add(Evento.fromCsv(line));
                } catch (Exception e) {
                    System.err.println("Linha inválida ignorada em " + FILE + ": " + line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro lendo " + FILE, e);
        }
        return eventos;
    }

    public static void saveAll(List<Evento> eventos) {
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(FILE))) {
            for (Evento e : eventos) {
                bw.write(e.toCsv());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro salvando " + FILE, e);
        }
    }
}