package com.unisul.eventos.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Participacao {
    private final UUID id;
    private final UUID usuarioId;
    private final UUID eventoId;
    private final LocalDateTime dataConfirmacao;

    // ─────────────── CONSTRUTOR (nova participação) ───────────────
    public Participacao(UUID usuarioId, UUID eventoId) {
        this.id = UUID.randomUUID();
        this.usuarioId = usuarioId;
        this.eventoId = eventoId;
        this.dataConfirmacao = LocalDateTime.now();
    }

    // ─────────────── CONSTRUTOR PRIVADO (reidratação CSV) ───────────────
    private Participacao(UUID id, UUID usuarioId, UUID eventoId, LocalDateTime dataConfirmacao) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.eventoId = eventoId;
        this.dataConfirmacao = dataConfirmacao;
    }

    // ─────────────── GETTERS ───────────────
    public UUID getId() { return id; }
    public UUID getUsuarioId() { return usuarioId; }
    public UUID getEventoId() { return eventoId; }
    public LocalDateTime getDataConfirmacao() { return dataConfirmacao; }

    // ─────────────── CSV ───────────────
    // Formato: id;usuarioId;eventoId;dataConfirmacao
    public String toCsv() {
        return String.join(";",
                id.toString(),
                usuarioId.toString(),
                eventoId.toString(),
                dataConfirmacao.toString()
        );
    }

    public static Participacao fromCsv(String line) {
        String[] p = line.split(";", -1);
        if (p.length < 4) throw new IllegalArgumentException("Linha inválida para Participacao: " + line);
        return new Participacao(
                UUID.fromString(p[0]), // id
                UUID.fromString(p[1]), // usuário
                UUID.fromString(p[2]), // evento
                LocalDateTime.parse(p[3]) // data
        );
    }

    @Override
    public String toString() {
        return "Participacao{" +
                "id=" + id +
                ", usuarioId=" + usuarioId +
                ", eventoId=" + eventoId +
                ", dataConfirmacao=" + dataConfirmacao +
                '}';
    }
}