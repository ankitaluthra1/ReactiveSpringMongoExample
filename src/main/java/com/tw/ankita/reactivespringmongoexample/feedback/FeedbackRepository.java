package com.tw.ankita.reactivespringmongoexample.feedback;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeedbackRepository extends MongoRepository<FeedBack,String> {

   public FeedBack findByEmployeeId(Long id);
}
