package com.example.pinkbullmakeup.Entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.UniqueElements;

@Embeddable
public class Shade {
    private String shadeName;
    private String imageUrl;

    public Shade() {
    }

    public Shade(String shadeName, String imageUrl) {
        this.shadeName = shadeName;
        this.imageUrl = imageUrl;
    }

    public String getShadeName() {
        return shadeName;
    }

    public void setShadeName(String shadeName) {
        this.shadeName = shadeName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shade shade = (Shade) o;

        return shadeName != null ? shadeName.equals(shade.shadeName) : shade.shadeName == null;
    }

    @Override
    public int hashCode() {
        return shadeName != null ? shadeName.hashCode() : 0;
    }

}
