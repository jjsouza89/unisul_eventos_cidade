package com.unisul.eventos.app;

import com.unisul.eventos.model.CategoriaEvento;
import com.unisul.eventos.model.Evento;
import com.unisul.eventos.model.Usuario;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    // ─────────────── ESTADO EM MEMÓRIA ───────────────
    private static final List<Usuario> usuarios = new ArrayList<>();
    private static final List<Evento> eventos = new ArrayList<>();

    // Formato de data/hora para o console
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        loopMenu();
        System.out.println("Até mais!");
    }

    // ─────────────── MENU PRINCIPAL ───────────────
    private static void loopMenu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Sistema de Eventos (Console) ===");
            System.out.println("1) Cadastrar usuário");
            System.out.println("2) Cadastrar evento");
            System.out.println("3) Listar eventos");
            System.out.println("9) Sair");
            System.out.print("Opção: ");
            String op = sc.nextLine().trim();

            switch (op) {
                case "1":
                    cadastrarUsuario(sc);
                    break;
                case "2":
                    cadastrarEvento(sc);
                    break;
                case "3":
                    listarEventos();
                    break;
                case "9":
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    // ─────────────── FLUXOS ───────────────

    private static void cadastrarUsuario(Scanner sc) {
        System.out.println("\n>>> Cadastro de Usuário");
        String nome = readLine(sc, "Nome: ");
        String email = readLine(sc, "Email: ");
        String telefone = readLine(sc, "Telefone: ");
        String senha = readLine(sc, "Senha: ");

        Usuario u = new Usuario(nome, email, telefone);
        u.definirSenha(senha);
        usuarios.add(u);

        System.out.println("Usuário cadastrado com sucesso! ID: " + u.getId());
    }

    private static void cadastrarEvento(Scanner sc) {
        System.out.println("\n>>> Cadastro de Evento");
        String nome = readLine(sc, "Nome: ");
        String endereco = readLine(sc, "Endereço: ");

        CategoriaEvento categoria = escolherCategoria(sc);

        LocalDateTime horario = readDateTime(sc,
                "Horário (formato yyyy-MM-dd HH:mm, ex: 2025-09-20 20:00): ");

        String duracaoStr = readLine(sc, "Duração (minutos) [vazio = 120]: ");
        int duracaoMin = duracaoStr.isBlank() ? 120 : Integer.parseInt(duracaoStr);

        String descricao = readLine(sc, "Descrição: ");

        Evento e = new Evento(nome, endereco, categoria, horario, duracaoMin, descricao);
        eventos.add(e);

        System.out.println("Evento cadastrado! ID: " + e.getId());
    }

    private static void listarEventos() {
        System.out.println("\n>>> Eventos cadastrados");
        if (eventos.isEmpty()) {
            System.out.println("(nenhum evento ainda)");
            return;
        }

        eventos.sort(Comparator.comparing(Evento::getHorario));

        for (Evento e : eventos) {
            String status;
            if (e.isOngoing()) status = "AGORA";
            else if (e.isPast()) status = "PASSADO";
            else status = "FUTURO";

            System.out.printf("- %s [%s]\n", status, e.toString());
        }
    }

    // ─────────────── HELPERS ───────────────

    private static String readLine(Scanner sc, String label) {
        System.out.print(label);
        return sc.nextLine().trim();
    }

    private static LocalDateTime readDateTime(Scanner sc, String label) {
        while (true) {
            String s = readLine(sc, label);
            try {
                return LocalDateTime.parse(s, DTF);
            } catch (Exception e) {
                System.out.println("Data/hora inválida. Use o formato yyyy-MM-dd HH:mm");
            }
        }
    }

    private static CategoriaEvento escolherCategoria(Scanner sc) {
        System.out.println("Categorias:");
        CategoriaEvento[] values = CategoriaEvento.values();
        for (int i = 0; i < values.length; i++) {
            System.out.printf("  %d) %s\n", i + 1, values[i].name());
        }
        while (true) {
            String s = readLine(sc, "Escolha (1-" + values.length + "): ");
            try {
                int idx = Integer.parseInt(s);
                if (idx >= 1 && idx <= values.length) {
                    return values[idx - 1];
                }
            } catch (NumberFormatException ignored) {}
            System.out.println("Opção inválida.");
        }
    }
}