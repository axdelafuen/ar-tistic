import java.util.concurrent.atomic.AtomicInteger
import java.util.Date

data class User(val id: Int, val name: String, val profilePicture:String, val email: String, val password:String, val birthDate:Date, val subscribes:HashMap<Int,User>, val subscribers: HashMap<Int, User>, val nbReport:Int)

class UserDao {

    val users = hashMapOf(
        0 to User(id= 0, name = "Alice", profilePicture = "./img/pp/Alice.jpg", email = "alice@alice.kt", password="1234", birthDate=Date(1999,2,2) , subscribes= hashMapOf(), subscribers = hashMapOf(), nbReport = 0 ),
        1 to User(id= 1, name = "Fredo", profilePicture = "./img/pp/Fredo.jpg", email = "fred@fred.kt", password="aaaa123", birthDate= Date(2003,1,1), subscribes = hashMapOf(), subscribers = hashMapOf(), nbReport = 0 ),
    )

    var lastId: AtomicInteger = AtomicInteger(users.size - 1)

    fun save(name: String, profilePicture:String, email: String, password:String, birthDate:Date, subscribes:HashMap<Int, User>, subscribers: HashMap<Int, User>, nbReport:Int ) {
        val id = lastId.incrementAndGet()
        users.put(id, User(id = id, name = name,  profilePicture=profilePicture, email=email, password=password, birthDate=birthDate, subscribes=subscribes, subscribers=subscribers, nbReport=nbReport))
    }

    fun findById(id: Int): User? {
        return users[id]
    }

    fun update(id: Int, user: User) {
        users.put(id, User(id = id, name = user.name,  profilePicture=user.profilePicture, email=user.email, password=user.password, birthDate=user.birthDate, subscribes=user.subscribes, subscribers=user.subscribers, nbReport=user.nbReport))
    }

    fun delete(id: Int) {
        users.remove(id)
    }

}