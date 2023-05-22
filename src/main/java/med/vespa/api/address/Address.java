package med.vespa.api.address;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String street;
    private String number;
    private String zip;
    private String state;
    private String city;
    private String complement;

    public Address(AddressDTO data) {
        this.street = data.street();
        this.number = data.number();
        this.zip = data.zip();
        this.state = data.state();
        this.city = data.city();
        this.complement = data.complement();
    }

    public void update(AddressDTO data) {
        if (data.street() != null) {
            this.street = data.street();
        }

        if (data.number() != null) {
            this.number = data.number();
        }

        if (data.zip() != null) {
            this.zip = data.zip();
        }

        if (data.state() != null) {
            this.state = data.state();
        }

        if (data.city() != null) {
            this.city = data.city();
        }

        if (data.complement() != null) {
            this.complement = data.complement();
        }
    }
}
