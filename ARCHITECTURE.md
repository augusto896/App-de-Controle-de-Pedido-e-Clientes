# 📐 Arquitetura - Documentação Técnica

## 🏗️ Padrão MVVM (Model-View-ViewModel)

```
┌─────────────────────────────────────────────────────────────┐
│                         UI Layer                             │
│  (Screens, Components, Composables)                         │
└────────┬────────────────────────────────────────────────────┘
         │ observes           ┌──────────────────────────┐
         │ ◄──────────────────┤    ViewModel             │
         │                    │ - State Management       │
         │                    │ - Business Logic         │
         │                    │ - Event Handling         │
         │                    └──────┬───────────────────┘
         │                           │ uses
         │                           ▼
         │                    ┌──────────────────────────┐
         │                    │   Repository            │
         │                    │ - Data Abstraction      │
         │                    │ - Source Management     │
         │                    └──────┬───────────────────┘
         │                           │
         │              ┌────────────┴────────────┐
         │              ▼                          ▼
         │      ┌──────────────────┐      ┌──────────────────┐
         │      │  Local Database  │      │  DataStore       │
         │      │  (Room)          │      │  (Preferences)   │
         │      └──────────────────┘      └──────────────────┘
```

## 📦 Estrutura de Pacotes

```
com.example.a11dejunho/
├── data/
│   ├── database/
│   │   ├── AppDatabase.kt          # Configuração do Room
│   │   └── DatabaseCallback.kt     # Callbacks de inicialização
│   ├── dao/
│   │   ├── UserDao.kt              # Data Access - Usuários
│   │   ├── ClientDao.kt            # Data Access - Clientes
│   │   ├── ProductDao.kt           # Data Access - Produtos
│   │   └── OrderDao.kt             # Data Access - Pedidos
│   ├── entity/
│   │   ├── UserEntity.kt           # Tabela: users
│   │   ├── ClientEntity.kt         # Tabela: clients
│   │   ├── ProductEntity.kt        # Tabela: products
│   │   └── OrderEntity.kt          # Tabela: orders
│   └── repository/
│       ├── UserRepository.kt       # Abstração - Usuários
│       ├── ClientRepository.kt     # Abstração - Clientes
│       ├── ProductRepository.kt    # Abstração - Produtos
│       └── OrderRepository.kt      # Abstração - Pedidos
│
├── domain/
│   └── model/
│       ├── User.kt                 # Modelo - Usuário
│       ├── Client.kt               # Modelo - Cliente
│       ├── Product.kt              # Modelo - Produto
│       └── Order.kt                # Modelo - Pedido
│
├── ui/
│   ├── screens/
│   │   ├── LoginScreen.kt          # Tela de login
│   │   ├── HomeScreen.kt           # Tela principal
│   │   ├── ClientListScreen.kt     # Lista de clientes
│   │   ├── ClientFormScreen.kt     # Formulário de cliente
│   │   ├── ProductListScreen.kt    # Lista de produtos
│   │   ├── ProductFormScreen.kt    # Formulário de produto
│   │   ├── OrderListScreen.kt      # Lista de pedidos
│   │   ├── OrderFormScreen.kt      # Formulário de pedido
│   │   └── SettingsScreen.kt       # Tela de configurações
│   ├── components/
│   │   ├── ConfirmDialog.kt        # Diálogo de confirmação
│   │   └── AppTextField.kt         # TextField customizado
│   ├── viewmodel/
│   │   ├── LoginViewModel.kt       # ViewModel - Login
│   │   ├── ClientViewModel.kt      # ViewModel - Clientes
│   │   ├── ProductViewModel.kt     # ViewModel - Produtos
│   │   ├── OrderViewModel.kt       # ViewModel - Pedidos
│   │   └── SettingsViewModel.kt    # ViewModel - Configurações
│   ├── navigation/
│   │   └── AppNavigation.kt        # Configuração de rotas
│   └── theme/
│       └── Theme.kt                # Tema do app
│
├── util/
│   ├── Constants.kt                # Constantes globais
│   └── Extensions.kt               # Extensões úteis
│
└── MainActivity.kt                 # Activity principal
```

## 🔄 Fluxo de Dados

### Exemplo: Listar Clientes

```
ClientListScreen
    │
    └─► ClientViewModel
        │
        ├─► uiState (StateFlow)
        │   └─► clients: List<Client>
        │
        └─► ClientRepository.getAllClients()
            │
            └─► ClientDao.getAllClients()
                │
                └─► Room Database
                    │
                    └─► clients table
                        │
                        ├─► ClientEntity 1
                        ├─► ClientEntity 2
                        └─► ClientEntity N
                            │
                            │ convert to
                            ▼
                        Client domain models
                        │
                        │ emit via Flow
                        ▼
                    ClientRepository
                        │
                        │ collect in
                        ▼
                    ClientViewModel (update state)
                        │
                        │ recompose
                        ▼
                    ClientListScreen (renders list)
```

## 🧵 Padrões de Concorrência

### Coroutines + Flow + StateFlow

```kotlin
// ViewModel recolhe dados de forma reativa
viewModelScope.launch {
    // Operação assincronamente segura
    clientRepository.getAllClients().collect { clients ->
        // Atualizar estado quando dados chegam
        _uiState.value = _uiState.value.copy(
            clients = clients,
            isLoading = false
        )
    }
}

// UI observa o estado
val uiState by viewModel.uiState.collectAsState()
```

## 🗄️ Esquema do Banco de Dados

### users
```
| id (PK) | username    | password |
|---------|-------------|----------|
| 1       | admin       | 1234     |
| 2       | user        | 1234     |
```

### clients
```
| id (PK) | name         | phone      | email            | city          |
|---------|-------------|------------|-----------------|---------------|
| 1       | João Silva  | 11987654321 | joao@email.com | São Paulo    |
| 2       | Maria Costa | 21998765432 | maria@email.com | Rio de Janeiro|
```

### products
```
| id (PK) | name     | description         | price   | stockQuantity |
|---------|----------|-------------------|---------|---------------|
| 1       | Notebook | Notebook Intel i5 | 3500.00 | 10           |
| 2       | Monitor  | Monitor 24" Full HD | 800.00 | 20           |
```

### orders
```
| id(PK)| clientId(FK)| productId(FK)| quantity| orderDate  | orderTime| totalValue|
|------|------------|-------------|---------|-----------|----------|-----------|
| 1    | 1          | 1          | 2       | 2024-06-11| 14:30    | 7000.00  |
| 2    | 2          | 2          | 1       | 2024-06-11| 15:45    | 800.00   |
```

## 🔐 Relacionamentos

### Foreign Keys
- **orders.clientId** → clients.id (CASCADE DELETE)
- **orders.productId** → products.id (CASCADE DELETE)

### Índices para Performance
- Index em orders.clientId
- Index em orders.productId

## 🌍 Navigation Graph

```
login ──────────► home
                   │
            ┌──────┼──────┬──────┐
            ▼      ▼      ▼      ▼
        clients products orders settings
            │      │      │
            ├─────┬┴────┬─┴──────┤
            ▼     ▼     ▼        ▼
        client_ product_ order_
        form    form      form
```

## 💾 DataStore - Estrutura

```
preferences_pb
├── dark_theme (Boolean)
├── user_name (String)
├── notifications_enabled (Boolean)
└── sort_by (String: "name" | "date")
```

## 🎨 Componentes Reutilizáveis

### ConfirmDeleteDialog
```kotlin
@Composable
fun ConfirmDeleteDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    title: String = "Confirmar exclusão",
    message: String = "Tem certeza?"
)
```

### AppOutlinedTextField
```kotlin
@Composable
fun AppOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier.fillMaxWidth(),
    isError: Boolean = false,
    errorText: String = "",
    ...
)
```

## 🧪 Validações Implementadas

### Login
- ✅ Usuário obrigatório
- ✅ Senha obrigatória
- ✅ Senha mínimo 4 caracteres
- ✅ Autenticação contra banco

### Clientes
- ✅ Nome obrigatório
- ✅ Telefone obrigatório
- ✅ Email obrigatório
- ✅ Cidade obrigatória

### Produtos
- ✅ Nome obrigatório
- ✅ Descrição obrigatória
- ✅ Preço obrigatório (conversão Double)
- ✅ Quantidade obrigatória (conversão Int)

### Pedidos
- ✅ Cliente selecionado
- ✅ Produto selecionado
- ✅ Quantidade obrigatória
- ✅ Data obrigatória
- ✅ Hora obrigatória
- ✅ Valor total obrigatório

## 🔍 Recursos de Busca

### Implementação
- Busca executada em tempo real conforme digita
- Query com LIKE (%query%) em toda string
- Case-insensitive por padrão

### Tabelas com busca
- ✅ Clientes (por nome)
- ✅ Produtos (por nome)
- ✅ Pedidos (por cliente ou produto)

## ⚡ Performance

### Otimizações
- Índices em foreign keys
- LazyColumn para renderização eficiente
- Flow reativo para atualização de UI
- StateFlow para estado isolado
- Paginação opcional (não implementada)

### Limites
- Até 10.000 registros confortavelmente
- Busca O(n) em memória
- Índices melhoram busca em banco

## 🔄 Lifecycle

### Telas
- onCreate: Inicializa ViewModel
- onStart: Observa StateFlow
- onResume: UI reativa
- onPause: Continua coletando
- onDestroy: ViewModel cancela coroutines

### Database
- Singleton pattern
- Inicialização lazy
- Destruição com app

## 📊 Dependências Principais

```
androidx.compose:compose-bom=2024.09.00
androidx.room:room-runtime=2.6.1
androidx.navigation:navigation-compose=2.7.5
androidx.datastore:datastore-preferences=1.1.0
androidx.lifecycle:lifecycle-viewmodel-compose=2.7.0
```

---

**Documentação Técnica - Sistema de Gestão v1.0** 📚

