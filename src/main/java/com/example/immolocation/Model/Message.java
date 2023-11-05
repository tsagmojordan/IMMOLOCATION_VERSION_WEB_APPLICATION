package com.example.immolocation.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class Message {
@Id
@GeneratedValue(strategy= GenerationType.IDENTITY)
private int Id_Message;
@Transient
    private MessageType type;
    private String content="Contenu";
    @Transient
    private String sender="sender";

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
    @ManyToOne
    Bailleur bailleur;
    @ManyToOne
  Locataire locataire;

}
