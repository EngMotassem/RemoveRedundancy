package elements;

/**
 * Created by MohamedDev on 5/7/2017.
 */

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MohamedDev on 4/16/2017.
 */

public class Contact implements Parcelable {
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    private String phone;
    private String Url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;


    /**
     * Standard basic constructor for non-parcel
     * object creation
     */
    public Contact() {
        ;
    }


    public Contact(Parcel in) {
        readFromParcel(in);
    }









    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        // We just need to write each field into the
        // parcel. When we read from parcel, they
        // will come back in the same order
        dest.writeString(phone);

        dest.writeString(Url);
        dest.writeString(name);




    }

    /**
     * Called from the constructor to create this
     * object from a parcel.
     *
     * @param in parcel from which to re-create object
     */
    private void readFromParcel(Parcel in) {

        // We just need to read back each
        // field in the order that it was
        // written to the parcel
        phone = in.readString();


        Url = in.readString();
name = in.readString();

    }

    /**
     * This field is needed for Android to be able to
     * create new objects, individually or as arrays.
     * <p>
     * This also means that you can use use the default
     * constructor to create the object and use another
     * method to hyrdate it as necessary.
     * <p>
     * I just find it easier to use the constructor.
     * It makes sense for the way my brain thinks ;-)
     */
    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() {
                public Contact createFromParcel(Parcel in) {
                    return new Contact(in);
                }

                public Contact[] newArray(int size) {
                    return new Contact[size];
                }
            };
}