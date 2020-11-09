package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.patient.Address;
import seedu.address.model.patient.Birthdate;
import seedu.address.model.patient.BloodType;
import seedu.address.model.patient.Email;
import seedu.address.model.patient.Gender;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Phone;
import seedu.address.model.patient.Remark;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Patient objects.
 */
public class PatientBuilder {

    public static final String DEFAULT_NAME = "Alexandra Loise";
    public static final String DEFAULT_GENDER = "FEMALE";
    public static final String DEFAULT_BIRTHDATE = "2000-11-11";
    public static final String DEFAULT_BLOODTYPE = "AB+";
    public static final String DEFAULT_PHONE = "9831920";
    public static final String DEFAULT_EMAIL = "alex@gmail.com";
    public static final String DEFAULT_ADDRESS = "144, Bedok West Ave 2, #08-143";
    public static final String DEFAULT_REMARK = "Allergic to flower";

    private Name name;
    private Gender gender;
    private Birthdate birthdate;
    private BloodType bloodtype;
    private Phone phone;
    private Email email;
    private Address address;
    private Remark remark;
    private Set<Tag> tags;

    /**
     * Creates a {@code PatientBuilder} with the default details.
     */
    public PatientBuilder() {
        name = new Name(DEFAULT_NAME);
        gender = new Gender(DEFAULT_GENDER);
        birthdate = new Birthdate(DEFAULT_BIRTHDATE);
        bloodtype = new BloodType(DEFAULT_BLOODTYPE);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        remark = new Remark(DEFAULT_REMARK);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PatientBuilder with the data of {@code patientToCopy}.
     */
    public PatientBuilder(Patient patientToCopy) {
        name = patientToCopy.getName();
        gender = patientToCopy.getGender();
        birthdate = patientToCopy.getBirthdate();
        bloodtype = patientToCopy.getBloodType();
        phone = patientToCopy.getPhone();
        email = patientToCopy.getEmail();
        address = patientToCopy.getAddress();
        remark = patientToCopy.getRemark();
        tags = new HashSet<>(patientToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Patient} that we are building.
     */
    public PatientBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code Patient} that we are building.
     */
    public PatientBuilder withGender(String gender) {
        this.gender = new Gender(gender);
        return this;
    }

    /**
     * Sets the {@code Birthdate} of the {@code Patient} that we are building.
     */
    public PatientBuilder withBirthdate(String birthdate) {
        this.birthdate = new Birthdate(birthdate);
        return this;
    }

    /**
     * Sets the {@code BloodType} of the {@code Patient} that we are building.
     */
    public PatientBuilder withBloodType(String bloodtype) {
        this.bloodtype = new BloodType(bloodtype);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Patient} that we are building.
     */
    public PatientBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Patient} that we are building.
     */
    public PatientBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Patient} that we are building.
     */
    public PatientBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Patient} that we are building.
     */
    public PatientBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Patient} that we are building.
     */
    public PatientBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    public Patient build() {
        return new Patient(name, gender, birthdate, bloodtype, phone, email, address, remark, tags);
    }

}
