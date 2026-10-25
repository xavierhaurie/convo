package com.convo.converse.entity;

import jakarta.persistence.*;

@Entity
public class Utterance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "reply_to")
    private Utterance replyTo;
    @Column(name = "content")
    private String content;
    @Column(name = "from_person_id")
    private Long fromPersonId;
    @Column(name = "to_person_id")
    private Long toPersonId;

    public Utterance() {
    }

    public Utterance(Utterance replyTo, String content, Long fromPersonId, Long toPersonId) {
        this.replyTo = replyTo;
        this.content = content;
        this.fromPersonId = fromPersonId;
        this.toPersonId = toPersonId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Utterance getReplayTo() {
        return replyTo;
    }

    public void setPreviousReply(Utterance previousUtterance) {
        this.replyTo = previousUtterance;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getFromPersonId() {
        return fromPersonId;
    }

    public void setFromId(Long fromPersonId) {
        this.fromPersonId = fromPersonId;
    }

    public Long getToPersonId() {
        return toPersonId;
    }

    public void setToPersonId(Long toPersonId) {
        this.toPersonId = toPersonId;
    }
}
