# 🚀 Guia de Uso - Sistema de Gestão

## 📱 Instalação e Execução

### 1. Compilar o APK
```bash
cd 11dejunho
./gradlew assembleDebug
```

O APK será gerado em: `app/build/outputs/apk/debug/app-debug.apk`

### 2. Instalar em um emulador ou dispositivo
```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

### 3. Executar pelo Android Studio
- Abrir o projeto em Android Studio
- Clicar em `Run > Run 'app'`
- Selecionar o emulador ou dispositivo

## 👤 Credenciais de Teste

### Usuários pré-cadastrados:
```
Usuário: admin
Senha: 1234

Usuário: user
Senha: 1234
```

## 🎯 Fluxo de Primeiras Ações

### Primeira Execução
1. **Tela de Login**: Faça login com um dos usuários acima
2. **Tela Principal**: Você será direcionado à tela de navegação

### Fluxo Sugerido
1. **Cadastro de Clientes**
   - Clique em "Clientes"
   - Clique no botão "+", para adicionar novo cliente
   - Preencha todos os campos
   - Clique em "Salvar"

2. **Cadastro de Produtos**
   - Clique em "Produtos"
   - Clique no botão "+", para adicionar novo produto
   - Preencha os dados: nome, descrição, preço e quantidade
   - Clique em "Salvar"

3. **Criação de Pedidos**
   - Clique em "Pedidos"
   - Clique no botão "+", para criar novo pedido
   - Selecione um cliente no dropdown
   - Selecione um produto no dropdown
   - Digite a quantidade
   - Clique no botão "..." ao lado de "Data do Pedido" para abrir o DatePicker
   - Clique no botão "..." ao lado de "Hora do Pedido" para abrir o TimePicker
   - Digite o valor total
   - Clique em "Salvar"

4. **Configurações**
   - Clique em "Configurações"
   - Altere as preferências conforme desejado
   - As alterações são salvas automaticamente

## 🔄 Operações Disponíveis

### Clientes
- ✅ Listar com busca
- ✅ Criar novo
- ✅ Editar existente
- ✅ Deletar com confirmação

### Produtos
- ✅ Listar com busca
- ✅ Criar novo
- ✅ Editar existente
- ✅ Deletar com confirmação

### Pedidos
- ✅ Listar com busca
- ✅ Criar novo (com seleção de cliente e produto)
- ✅ Usar DatePicker / TimePicker
- ✅ Editar existente
- ✅ Deletar com confirmação

### Configurações
- ✅ Tema escuro/claro
- ✅ Nome do usuário
- ✅ Notificações on/off
- ✅ Preferência de ordenação

## 📊 Estrutura de Dados

### Clientes
- Nome
- Telefone
- Email
- Cidade

### Produtos
- Nome
- Descrição
- Preço
- Quantidade em estoque

### Pedidos
- Cliente (relação)
- Produto (relação)
- Quantidade
- Data do pedido
- Hora do pedido
- Valor total

## ⚙️ Configurações do Sistema

### Requisitos Mínimos
- Android 7.0 (API 24)
- 50 MB de espaço livre

### Requisitos Recomendados
- Android 13+ (API 33+)
- 100 MB de espaço livre
- Display 5" ou superior

## 🛠️ Troubleshooting

### Problema: App não inicia
**Solução**: Limpe o cache
```bash
./gradlew clean build
adb uninstall com.example.a11dejunho
adb install app/build/outputs/apk/debug/app-debug.apk
```

### Problema: Banco de dados não funciona
**Solução**: Reinstale o app
```bash
adb uninstall com.example.a11dejunho
rm -rf app/build
./gradlew assembleDebug
adb install app/build/outputs/apk/debug/app-debug.apk
```

### Problema: DatePicker/TimePicker não abre
**Solução**: Verifique se o emulador tem locales corretos instalados

## 💾 Dados Persistidos

### Banco de Dados (Room)
- Usuários
- Clientes
- Produtos
- Pedidos

Local: `/data/data/com.example.a11dejunho/databases/app_database`

### Configurações (DataStore)
- Tema
- Nome do usuário
- Status de notificações
- Preferência de ordenação

Local: `/data/data/com.example.a11dejunho/datastore/settings.preferences_pb`

## 📝 Notas Importantes

1. **Senhas**: Armazenadas em plain text (apenas para demo)
2. **Validação**: Email e telefone não são validados rigorosamente
3. **Performance**: Otimizações para 1000+ registros
4. **Backup**: Não há backup automático, apenas banco de dados
5. **Sincronização**: Dados sincronizados localmente apenas

## 🆘 Suporte

Em caso de problemas:
1. Visite o arquivo `README.md` para documentação completa
2. Verifique os logs do Android Studio
3. Limpe cache e dados do app

---

**Desenvolvido em Kotlin com Jetpack Compose** ❤️

