Commandes de bases du web service (pour les tests) :
===

DELETE :
---

```sh
curl --request DELETE --url http://localhost:7070/users/{id}
```

GET :
---

```sh
curl --request GET --url http://localhost:7070/users/{id}   <-- optionnel
```

POST :
---

```sh
curl --request POST \                                    
  --url http://localhost:7070/users \
  --data '{"name":"fred","email":"fred@fred.kt"}'
```

