package com.unisul.eventos.repository;

import com.unisul.eventos.model.Participacao;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ParticipacaoRepository {
    private static final String FILE = "participacoes.data";

    /** Carrega todas as participações do arquivo */
    public static List<Participacao> load() {
        List<Participacao> list = new ArrayList<>();
        Path path = Paths.get(FILE);
        if (!Files.exists(path)) return list;

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isBlank()) {
                    list.add(Participacao.fromCsv(line));
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar participações: " + e.getMessage());
        }
        return list;
    }

    /** Salva todas as participações no arquivo */
    public static void save(List<Participacao> participacoes) {
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(FILE))) {
            for (Participacao p : participacoes) {
                bw.write(p.toCsv());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar participações: " + e.getMessage());
        }
    }
}