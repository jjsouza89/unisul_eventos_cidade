package com.unisul.eventos.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.unisul.eventos.util.Util;

public class Evento {

    private final UUID id;
    private String nome;
    private String endereco;
    private CategoriaEvento categoria;
    private LocalDateTime horario;
    private int duracaoMinutos; // duração do evento em minutos
    private String descricao;

    // Construtor padrão (útil para frameworks/serialização)
    public Evento() {
        this.id = UUID.randomUUID();
        this.horario = LocalDateTime.now();
        this.duracaoMinutos = 120; // valor padrão
    }

    // Construtor “essencial”
    public Evento(String nome, String endereco, CategoriaEvento categoria,
                  LocalDateTime horario, String descricao) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.endereco = endereco;
        this.categoria = categoria;
        this.horario = horario;
        this.descricao = descricao;
        this.duracaoMinutos = 120; // padrão
    }

    // Construtor com duração definida
    public Evento(String nome, String endereco, CategoriaEvento categoria,
                  LocalDateTime horario, int duracaoMinutos, String descricao) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.endereco = endereco;
        this.categoria = categoria;
        this.horario = horario;
        this.duracaoMinutos = duracaoMinutos;
        this.descricao = descricao;
    }

    // Construtor privado para reidratar via CSV (persistência)
    private Evento(UUID id, String nome, String endereco,
                   CategoriaEvento categoria, LocalDateTime horario,
                   int duracaoMinutos, String descricao) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.categoria = categoria;
        this.horario = horario;
        this.duracaoMinutos = duracaoMinutos;
        this.descricao = descricao;
    }


    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public CategoriaEvento getCategoria() { return categoria; }
    public void setCategoria(CategoriaEvento categoria) { this.categoria = categoria; }
    public LocalDateTime getHorario() { return horario; }
    public void setHorario(LocalDateTime horario) { this.horario = horario; }
    public int getDuracaoMinutos() { return duracaoMinutos; }
    public void setDuracaoMinutos(int duracaoMinutos) { this.duracaoMinutos = duracaoMinutos; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    /** Retorna true se o evento ainda não começou (futuro). */
    public boolean isUpcoming() {
        return LocalDateTime.now().isBefore(this.horario);
    }

    /** Retorna true se o evento já acabou. */
    public boolean isPast() {
        return LocalDateTime.now().isAfter(this.horario.plusMinutes(duracaoMinutos));
    }

    /** Retorna true se o evento está acontecendo agora. */
    public boolean isOngoing() {
        LocalDateTime agora = LocalDateTime.now();
        return (agora.isAfter(horario) || agora.isEqual(horario)) &&
                agora.isBefore(horario.plusMinutes(duracaoMinutos));
    }

    // Formato: id;nome;endereco;categoria;horario;duracaoMinutos;descricao
    public String toCsv() {
        return String.join(";",
                id.toString(),
                Util.nvl(nome),
                Util.nvl(endereco),
                categoria.name(),
                horario.toString(),
                String.valueOf(duracaoMinutos),
                Util.nvl(descricao)
        );
    }

    public static Evento fromCsv(String line) {
        String[] p = line.split(";", -1);
        if (p.length < 7) throw new IllegalArgumentException("Linha inválida para Evento: " + line);
        return new Evento(
                UUID.fromString(p[0]),
                Util.vazioParaNull(p[1]),
                Util.vazioParaNull(p[2]),
                CategoriaEvento.valueOf(p[3]),
                LocalDateTime.parse(p[4]),
                Integer.parseInt(p[5]),
                Util.vazioParaNull(p[6])
        );
    }

    @Override
    public String toString() {
        return "id " + id + " "+ nome + " [" + categoria + "] em " + endereco +
                " às " + horario +
                " (" + duracaoMinutos + " min) - " +
                descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Evento)) return false;
        Evento evento = (Evento) o;
        return id.equals(evento.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}