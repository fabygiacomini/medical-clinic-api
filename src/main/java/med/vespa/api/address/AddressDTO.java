package med.vespa.api.address;

public record AddressDTO(
        String street,
        String number,
        String zip,
        String state,
        String city,
        String complement
) {
}
