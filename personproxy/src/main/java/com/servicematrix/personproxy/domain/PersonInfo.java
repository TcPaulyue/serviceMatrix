package com.servicematrix.personproxy.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonInfo {
    private String name;

    private Double Xloc;

    private Double Yloc;

    public String updatePersonInfo(String name, Double Xloc,Double Yloc) {
        this.name = name;
        this.Xloc = Xloc;
        this.Yloc = Yloc;
        System.out.println("PeopleDao.update()"+"  "+this.name+ " "+this.Xloc+"   "+this.Yloc);
        return this.name;
    }

    public void setPersonInfo(String name, Double Xloc,Double Yloc){
        this.name = name;
        this.Xloc = Xloc;
        this.Yloc = Yloc;
    }


    @Override
    public String toString(){
        return this.name+"  "+this.Xloc+"  "+ this.Yloc;
    }

}
