import com.example.classlib.Date
import java.io.Serializable;

public class User(var id: Int, var name: String, var profilePicture:String, var email: String, var password:String, var birthDate: Date, var subscribes:HashMap<Int,User>, var nbReport:Int) : Serializable {



}