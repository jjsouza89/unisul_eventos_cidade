package com.unisul.eventos.app;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Sistema de Eventos (via 1Console) ===");
        System.out.println("Escolha uma opção:");
        System.out.println("1) \uD83D\uDE4B Cadastrar usuário ");
        System.out.println("2) 📅 Cadastrar evento ");
        System.out.println("3) 📋 Listar eventos ");
        System.out.println("9) \uD83D\uDC4B Sair ");

        Scanner sc = new Scanner(System.in);
        System.out.print("👉 Opção: ");
        String op = sc.nextLine();
        System.out.println("✅ Você escolheu: " + op + " (apenas esqueleto por enquanto)");
    }
}
