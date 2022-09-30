Commande de base du web service (pour les tests) :
===

DELETE :
---

```sh
curl --request DELETE --url http://localhost:7070/users/1
```

GET :
---

```sh
curl --request GET --url http://localhost:7070/users/ ?1?
```

POST :
---

```sh
curl --request POST \                                    
  --url http://localhost:7070/users \
  --data '{"name":"fred","email":"fred@fred.kt"}'
```

