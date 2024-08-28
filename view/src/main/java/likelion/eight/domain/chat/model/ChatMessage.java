package likelion.eight.domain.chat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private Long chatroomId;
    private String sender;
    private String message;

    private long timestamp;

}