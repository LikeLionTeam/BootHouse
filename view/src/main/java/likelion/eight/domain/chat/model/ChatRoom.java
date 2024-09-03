package likelion.eight.domain.chat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom implements Serializable {
    private String id;
    private String name;
    private Set<String> users;
}