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
}
