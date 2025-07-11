package vn.quangkhongbiet.homestay_booking.domain.homestay.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "homestay_images")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomestayImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "homestay_id")
    private Homestay homestay;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "public_id")
    private String publicId;

    @PreRemove
    private void removeFromHomestay(){
        this.homestay.getImages().remove(this);
        this.homestay = null;
    }
}
