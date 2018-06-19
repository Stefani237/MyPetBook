package com.yaki.mypetbook2.map.Logic;

public class Pet {
    public static int counter;
    private String ownerId, type, breed, name, age, description, imageEncoded, ownerEmail, ownerPhoneNumber;

    public Pet(){}

    public Pet (String ownerId, String type, String breed, String name, String age, String description, String imageEncoded, String ownerEmail, String ownerPhoneNumber){
        this.ownerId = ownerId;
        this.type = type;
        this.breed = breed;
        this.name = name;
        this.age = age;
        this.description = description;
        this.imageEncoded = imageEncoded;
        this.ownerEmail = ownerEmail;
        this.ownerPhoneNumber = ownerPhoneNumber;
    }

    public Pet(Pet other){
        this.ownerId = other.getOwnerId();
        this.type = other.getType();
        this.breed = other.getBreed();
        this.name = other.getName();
        this.age = other.getAge();
        this.description = other.getDescription();
        this.imageEncoded = other.getImageEncoded();
        this.ownerEmail = other.getOwnerEmail();
        this.ownerPhoneNumber = other.getOwnerPhoneNumber();
    }

    public String getType() {
        return type;
    }

    public String getBreed() {
        return breed;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getDescription() {
        return description;
    }

    public String getImageEncoded() {
        return imageEncoded;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public String getOwnerPhoneNumber() {
        return ownerPhoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
