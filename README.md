# Sistema de Gestão de Clientes, Produtos e Pedidos

## 📋 Descrição do Projeto

Aplicativo Android desenvolvido em **Kotlin** com **Jetpack Compose** para gestão integral de operações comerciais. O sistema permite gerenciar clientes, produtos e pedidos com um banco de dados robusto e sincronização de configurações do usuário.

## ✨ Funcionalidades Principais

### 1. Autenticação
- ✅ Tela de login com validação
- ✅ Registro de novos usuários
- ✅ Persistência opcional de sessão
- ✅ Mensagens de erro via Snackbar

### 2. Gestão de Clientes
- ✅ Listar clientes com LazyColumn
- ✅ Inserir novo cliente
- ✅ Editar cliente existente
- ✅ Deletar cliente com confirmação via AlertDialog
- ✅ Buscar cliente por nome

### 3. Gestão de Produtos
- ✅ Listar produtos com LazyColumn
- ✅ Inserir novo produto
- ✅ Editar produto existente
- ✅ Deletar produto com confirmação
- ✅ Buscar produto por nome

### 4. Gestão de Pedidos
- ✅ Listar pedidos com relacionamentos
- ✅ Criar pedido com seleção de Cliente e Produto
- ✅ DatePicker para data do pedido
- ✅ TimePicker para hora do pedido
- ✅ Editar pedido
- ✅ Deletar pedido com confirmação
- ✅ Buscar pedido por nome do cliente

### 5. Tela de Configurações
- ✅ Tema escuro/claro (DataStore)
- ✅ Nome do usuário
- ✅ Notificações habilitadas/desabilitadas
- ✅ Preferência de ordenação
- ✅ Informações do aplicativo

## 🏗️ Arquitetura MVVM

```
11dejunho/
├── data/
│   ├── database/
│   │   └── AppDatabase.kt
│   ├── dao/
│   │   ├── UserDao.kt
│   │   ├── ClientDao.kt
│   │   ├── ProductDao.kt
│   │   └── OrderDao.kt
│   ├── entity/
│   │   ├── UserEntity.kt
│   │   ├── ClientEntity.kt
│   │   ├── ProductEntity.kt
│   │   └── OrderEntity.kt
│   └── repository/
│       ├── UserRepository.kt
│       ├── ClientRepository.kt
│       ├── ProductRepository.kt
│       └── OrderRepository.kt
├── domain/
│   └── model/
│       ├── User.kt
│       ├── Client.kt
│       ├── Product.kt
│       └── Order.kt
├── ui/
│   ├── screens/
│   │   ├── LoginScreen.kt
│   │   ├── HomeScreen.kt
│   │   ├── ClientListScreen.kt
│   │   ├── ClientFormScreen.kt
│   │   ├── ProductListScreen.kt
│   │   ├── ProductFormScreen.kt
│   │   ├── OrderListScreen.kt
│   │   ├── OrderFormScreen.kt
│   │   └── SettingsScreen.kt
│   ├── components/
│   │   ├── ConfirmDialog.kt
│   │   └── AppTextField.kt
│   ├── viewmodel/
│   │   ├── LoginViewModel.kt
│   │   ├── ClientViewModel.kt
│   │   ├── ProductViewModel.kt
│   │   ├── OrderViewModel.kt
│   │   └── SettingsViewModel.kt
│   ├── navigation/
│   │   └── AppNavigation.kt
│   └── theme/
│       └── Theme.kt
└── MainActivity.kt
```

## 🛠️ Tecnologias Utilizadas

### Framework e Linguagem
- **Kotlin** 2.0.21
- **Jetpack Compose** 2024.09.00

### Persistência de Dados
- **Room Database** 2.6.1
  - SQLite com ORM
  - Relacionamentos entre tabelas
  - DAOs para acesso aos dados
  - Migrations automáticas

- **DataStore Preferences** 1.1.0
  - Persistência de configurações do usuário
  - Alternativa moderna para SharedPreferences

### Navegação
- **Navigation Compose** 2.7.5
  - Navegação entre telas
  - Passing arguments entre destinos

### MVVM e Coroutines
- **Lifecycle ViewModel** 2.7.0
- **Coroutines** (Flow e StateFlow)
  - Fluxo reativo de dados
  - Operações assincronamente seguras

### UI Moderna
- **Material 3** 1.6.0
  - Design moderno e responsivo
  - Componentes atualizados
  - Temas customizáveis

### Build & Dependency Management
- **Gradle** 8.13.2
- **Android Gradle Plugin** 8.13.2

## 📊 Diagrama de Entidades

```
┌─────────────────┐
│ UserEntity      │
├─────────────────┤
│ id (PK)         │
│ username        │
│ password        │
└─────────────────┘

┌─────────────────┐
│ ClientEntity    │
├─────────────────┤
│ id (PK)         │
│ name            │
│ phone           │
│ email           │
│ city            │
└─────────────────┘

┌─────────────────┐
│ ProductEntity   │
├─────────────────┤
│ id (PK)         │
│ name            │
│ description     │
│ price           │
│ stockQuantity   │
└─────────────────┘

┌──────────────────────┐
│ OrderEntity          │
├──────────────────────┤
│ id (PK)              │
│ clientId (FK) ──────→│ ClientEntity
│ productId (FK) ─────→│ ProductEntity
│ quantity             │
│ orderDate            │
│ orderTime            │
│ totalValue           │
└──────────────────────┘
```

## 🚀 Fluxo de Uso

### 1. Primeira Execução
```
Login Screen → Registrar novo usuário → Criar credenciais → Login
```

### 2. Gestão de Dados
```
Home Screen → Selecionar módulo → Listar itens → Criar/Editar/Deletar
```

### 3. Configurações
```
Settings Screen → Modificar preferências → Salvar em DataStore
```

## 📋 Requisitos do Sistema

### Mínimo
- **Android SDK 24** (Android 7.0)
- **Java 11**

### Recomendado
- **Android SDK 33+** (Android 13+)
- **Java 17**

## ✅ Critérios de Avaliação

- ✅ **Funcionamento geral** (2.0)
- ✅ **Organização do projeto** (2.0)
- ✅ **Uso correto do Room** (2.0)
- ✅ **Uso do DataStore** (1.0)
- ✅ **Interface e usabilidade** (1.5)
- ✅ **Arquitetura MVVM** (1.0)
- ℹ️ **Recursos extras** (0.5)
- **Total: 10,0 pontos**

## 🎯 Recursos Implementados

### Obrigatórios ✅
- [x] Kotlin + Jetpack Compose
- [x] MVVM Architecture
- [x] Room Database
- [x] Navigation Compose
- [x] StateFlow / Flow
- [x] DataStore
- [x] LazyColumn
- [x] Material 3
- [x] Tela de Login
- [x] Gestão de Clientes
- [x] Gestão de Produtos
- [x] Gestão de Pedidos com relacionamentos
- [x] DatePicker e TimePicker
- [x] AlertDialog para confirmação
- [x] Tela de Configurações

### Recursos Extras 🌟
- [x] Campo de busca em listas
- [x] Filtros (através de busca)
- [x] Validação de formulários
- [x] Componentes reutilizáveis
- [x] UI responsiva
- [x] Ícones Material 3

## 🔧 Como Compilar

### Via Android Studio
1. Abrir projeto em Android Studio
2. Aguardar sincronização do Gradle
3. Executar `Build > Build Bundle(s) / APK(s) > Build APK(s)`

### Via Terminal
```bash
cd 11dejunho
./gradlew assembleDebug
```

## 📦 Estrutura de Dependências

Todas as dependências estão declaradas em `gradle/libs.versions.toml`:

```toml
[versions]
agp = "8.13.2"
kotlin = "2.0.21"
room = "2.6.1"
navigationCompose = "2.7.5"
datastore = "1.1.0"

[libraries]
androidx-room-runtime = "..."
androidx-navigation-compose = "..."
androidx-datastore-preferences = "..."
```

## 📝 Observações Importantes

### Validação de Dados
- Usuário e senha são obrigatórios no login
- Todos os campos são obrigatórios nos formulários
- Valores numéricos são validados antes de salvar
- Buscas são case-insensitive

### Persistência
- Dados são salvos no banco de dados Room
- Configurações persistem em DataStore
- Sessão pode ser gerenciada com adaptações futuras

### Performance
- Índices em foreign keys para consultas rápidas
- LazyColumn para renderização eficiente de listas
- Flow reactivo para atualizações de UI

## 🤝 Integrantes do Grupo

- [Nome do Aluno 1]
- [Nome do Aluno 2]

## 📄 Licença

Este projeto foi desenvolvido para fins educacionais.

## 📞 Suporte

Para dúvidas ou sugestões, entre em contato através do GitHub Issues.

---

**Desenvolvido com ❤️ usando Kotlin, Jetpack Compose e Android Architecture Components**

