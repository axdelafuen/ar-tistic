import com.example.classlib.Date
import java.io.Serializable;

public class User: Serializable {
    constructor( id: Int,  name: String,  profilePicture:String,  email: String,  password:String,  birthDate: Date, subscribes:HashMap<Int,User>,  nbReport:Int)
    {
        this.id=id
        this.email=email
        this.profilePicture=profilePicture
        this.name=name
        this.password=password
        this.birthDate=birthDate
        this.subscribes=subscribes
        this.nbReport=nbReport
    }
    var id: Int
    get() = field
    set(value) {field=value}
    var name: String
    var profilePicture:String
    var email: String
    var password:String
    var birthDate: Date
    var subscribes:HashMap<Int,User>
    var nbReport:Int


}