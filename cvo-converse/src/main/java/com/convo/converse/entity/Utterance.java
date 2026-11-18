package com.convo.converse.entity;

import jakarta.persistence.*;

@Entity
public class Utterance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "reply_to_id")
    private Long replyToId;
    @Column(name = "content")
    private String content;
    @Column(name = "from_person_id")
    private Long fromPersonId;
    @Column(name = "to_person_id")
    private Long toPersonId;

    public Utterance() {
    }

    public Utterance(Long replyToId, String content, Long fromPersonId, Long toPersonId) {
        this.replyToId = replyToId;
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

    public Long getReplyToId() {
        return replyToId;
    }

    public void setReplyToId(Long replyToId) {
        this.replyToId = replyToId;
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

    public void setFromPersonId(Long fromPersonId) {
        this.fromPersonId = fromPersonId;
    }

    public Long getToPersonId() {
        return toPersonId;
    }

    public void setToPersonId(Long toPersonId) {
        this.toPersonId = toPersonId;
    }
}
