package com.tw.ankita.reactivespringmongoexample.feedback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "feedbacks")
@AllArgsConstructor
@Data
public class FeedBack {

    @Id
    private String uuid;
    private String comment;
    private Long employeeId;

}
