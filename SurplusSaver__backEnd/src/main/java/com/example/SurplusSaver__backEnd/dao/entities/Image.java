//package com.example.SurplusSaver__backEnd.dao.entities;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Table(name = "image_table")
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class Image {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long imageID;
//
//    private String imageName;
//
//    private String type;
//
//    @Lob
//    @Column(name = "picByte", length = 5000)
//    private byte[] picByte;
//
//    @OneToOne
//    @JoinColumn(name = "users_id", referencedColumnName = "id")
//    private User users_id; //mapping with User entity
//}
