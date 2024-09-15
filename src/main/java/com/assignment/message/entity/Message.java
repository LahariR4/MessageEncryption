package com.assignment.message.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Message")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String originalMessage;

    @Column
    private String encryptedMessage;

    @Column
    private String encryptionType;

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                '}';
    }
}
