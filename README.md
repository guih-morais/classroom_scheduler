## Endpoints

A API possui endpoints para gerenciamento de **usuários**, **salas** e **reservas**.

### 👤 Usuários

| Método   | Endpoint                 | Descrição                |
| -------- | ------------------------ | ------------------------ |
| `GET`    | `/usuarios`              | Lista todos os usuários  |
| `GET`    | `/usuarios/{id}`         | Busca um usuário pelo ID |
| `POST`   | `/usuarios`              | Cria um novo usuário     |
| `PUT`    | `/usuarios`              | Atualiza um usuário      |
| `DELETE` | `/usuarios/deletar/{id}` | Deleta um usuário        |

#### Criar usuário

```http
POST /usuarios
```

```json
{
    "nome": "Fulana",
    "email": "fulana@email.com"
}
```

#### Atualizar usuário

```http
PUT /usuarios
```

```json
{
    "id": 3,
    "nome": "Nome",
    "email": "email@email.com"
}
```

---

### 🏫 Salas

| Método   | Endpoint              | Descrição              |
| -------- | --------------------- | ---------------------- |
| `GET`    | `/salas`              | Lista todas as salas   |
| `GET`    | `/salas/{id}`         | Busca uma sala pelo ID |
| `POST`   | `/salas`              | Cria uma nova sala     |
| `PUT`    | `/salas`              | Atualiza uma sala      |
| `DELETE` | `/salas/deletar/{id}` | Deleta uma sala        |

#### Criar sala

```http
POST /salas
```

```json
{
    "numeroSala": "5",
    "capacidade": "100"
}
```

#### Atualizar sala

```http
PUT /salas
```

```json
{
    "id": 3,
    "numeroSala": 10
}
```

---

### 📅 Reservas

| Método   | Endpoint                 | Descrição                 |
| -------- | ------------------------ | ------------------------- |
| `GET`    | `/reservas`              | Lista todas as reservas   |
| `GET`    | `/reservas/{id}`         | Busca uma reserva pelo ID |
| `POST`   | `/reservas`              | Cria uma nova reserva     |
| `PUT`    | `/reservas`              | Atualiza uma reserva      |
| `DELETE` | `/reservas/deletar/{id}` | Cancela uma reserva       |

#### Criar reserva

```http
POST /reservas
```

#### Atualizar reserva

```http
PUT /reservas
```

```json
{
    "id": 1,
    "inicioReserva": "10/08/26 - 14:00",
    "fimReserva": "10/08/26 - 16:00"
}
```

#### Cancelar reserva

```http
DELETE /reservas/deletar/{id}
```

---

### 📌 Resumo dos endpoints

| Recurso  | Listar          | Buscar               | Criar            | Atualizar       | Deletar/Cancelar                |
| -------- | --------------- | -------------------- | ---------------- | --------------- | ------------------------------- |
| Usuários | `GET /usuarios` | `GET /usuarios/{id}` | `POST /usuarios` | `PUT /usuarios` | `DELETE /usuarios/deletar/{id}` |
| Salas    | `GET /salas`    | `GET /salas/{id}`    | `POST /salas`    | `PUT /salas`    | `DELETE /salas/deletar/{id}`    |
| Reservas | `GET /reservas` | `GET /reservas/{id}` | `POST /reservas` | `PUT /reservas` | `DELETE /reservas/deletar/{id}` |

```

**Observação:** na coleção Postman enviada, o endpoint de buscar uma reserva está sem URL preenchida e o de criar reserva não possui corpo de requisição. Por isso, esses dois pontos não devem ser tratados como exemplos confirmados pela coleção.

Se você quiser deixar o README com aparência mais **profissional de projeto de portfólio**, eu também recomendo adicionar exemplos de `Request/Response`, códigos HTTP (`200`, `201`, `400`, `404`) e um botão/link para importar a coleção do Postman.
```
