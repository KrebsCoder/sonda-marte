## Desafio de sonda em marte 

Esse projeto foi um desafio proposto pela <a href="https://gist.github.com/elo7-developer/f0b91a7a98e5e65288b875ac6d376875">Elo7</a>, e essa é a minha realização dele.

---

## Como rodar a API

- Certifique-se de estar na pasta raiz do projeto, após isso, crie um arquivo chamado *probe.env*, ele servirá para o docker reconhecer variáveis de ambiente e subir o banco de dados, coloque as seguintes informações dentro dele:

```
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
POSTGRES_DB=db
POSTGRES_PORT=5432
DATABASE_HOST=database
```

- Digite o comando que irá baixar todas as dependências e iniciar o servidor (Obs: necessitará de ter as portas 8080 e 5432 liberadas):

```
docker-compose up
```

- Após estar com o servidor rodando, você poderá fazer requisições HTTP no localhost na porta 8080, utilizando o <a href="https://www.postman.com/">Postman</a> ou o <a href="https://insomnia.rest/download">Insomnia</a>.


## Métodos para o planeta

#### Obs: onde conter a palavra *name*, será o nome do planeta ou da sonda

- Criação de um planeta 

```
POST http://localhost:8080/planet
```

```json
{
	"name": "",
	"sizeX": 1,
	"sizeY": 1
}
```

- Ver o nome de todos os planetas

```
GET http://localhost:8080/planet
```

- Ver o nome de um planeta específico.
```
GET http://localhost:8080/planet/name
```

- Deletar um planeta específico.
```
DEL http://localhost:8080/planet/name
```

- Mudar o nome de um planeta.

```
PUT http://localhost:8080/planet/name
```

```json
{
	"name": ""
}
```


## Métodos para a sonda

- Criação de uma sonda

```
POST http://localhost:8080/probe
```

```json
{
	"name": "Sonda",
	"startPositionX": 3,
	"startPositionY": 3,
	"facingPosition": "EAST",
	"planetName": "Marte"
}
```

- Mover uma sonda

```
PUT http://localhost:8080/probe/move
```

```json
{
	"name": "Sonda",
	"movement": "MMRMMRMRRML"
}
```

- Mudar o nome de uma sonda. Obs: coloque o nome do planeta

```
PUT http://localhost:8080/probe/name
```

```json
{
	"name": "Sonda",
	"planetName": "Marte"
}
```

- Ver o nome de todas as sondas

```
GET http://localhost:8080/probe
```

- Ver o nome de uma sonda específica.
```
GET http://localhost:8080/probe/name
```

- Deletar uma sonda específica.
```
DEL http://localhost:8080/probe/name
```

