## Desafio de sonda em marte 

Esse projeto foi um desafio proposto pela <a href="https://gist.github.com/elo7-developer/f0b91a7a98e5e65288b875ac6d376875">Elo7</a>, e essa é a minha realização dele.

---

## Como rodar a API

- Certifique-se de estar na pasta raiz do projeto, após isso, crie um arquivo que termine com .env, ele servirá para o docker reconhecer variáveis de ambiente e subir o banco de dados, coloque as seguintes informações dentro dele:

```
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
POSTGRES_DB=db
POSTGRES_PORT=5432
DATABASE_HOST=database
```

- Digite o comando que irá baixar todas as dependências e iniciar o servidor (OBS: necessitará de ter as portas 8080 e 5432 liberadas):

```
docker-compose up
```

- Após estar com o servidor rodando, você poderá fazer requisições HTTP no localhost na porta 8080, utilizando o <a href="https://www.postman.com/">Postman</a> ou o <a href="https://insomnia.rest/download">Insomnia</a>.