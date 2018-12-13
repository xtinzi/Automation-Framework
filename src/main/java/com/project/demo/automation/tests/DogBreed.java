package com.project.demo.automation.tests;

public class DogBreed {
    private String breed;


    private String subBreed;

    public DogBreed(String breed, String subBreed) {
        this.breed = breed;
        this.subBreed = subBreed;

    }

    public DogBreed() {


    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getSubBreed() {
        return subBreed;
    }

    public void setSubBreed(String subBreed) {
        this.subBreed = subBreed;
    }

}
