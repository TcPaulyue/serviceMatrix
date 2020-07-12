package com.servicematrix.personproxy.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoffeeMessage {

    private String id;

    private String message;

    private PersonInfo personInfo;

    @Override
    public String toString(){
        return this.id+"  "+this.message+"  "+ this.personInfo.toString();
    }

}
