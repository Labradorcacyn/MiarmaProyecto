![image](https://cdn.discordapp.com/attachments/787363155494830091/907941779733618708/unknown.png)

# Readme de REALESTATE
### (BackEnd) 


> Esta api Rest con Spring tiene la intención de modelar el funcionamiento de una red social con diferentes entidades y
> asociaciones entre ellas. Partiendo de 'UserEntity' como base e interrelacionandolo con 'Publicaciones',
> que tienen la posibilidad de estar asociadas a una publicación, y puede realizar follow a los otros usuarios.
> Incluye Registro de usuarios y Login.

## Instalacion

Para poder arrancar la Api Rest es necesario disponer de una aplicación que permita ejecutar proyectos maven, un ejemplo
podría ser un IDE como Intellij u otro cualquiera.
Hacer un git clone del repositorio que está alojado en el usuario miguelcamposedu.
Todo el proecto está basado en Springboot y en java17
Puede probar los métodos en el archivo postman que incluye el proyecto.
```

## Pomp

Las dependencias que usaremos para eeste proyecto serán

- [H2DataBase] - Un gestor de base de datos que nos proporcionará una persistencia de datos en un xml
- [SpringWeb] - Será nuestro servidor web local
- [Lombok] - Una herramienta muy útil que proporcionará atajos a la hora de construir entidades y relaciones.
- [SpringJpa] - Será la manera que tengamos de de acceder a la base de datos basada en Hibernate
- [JavaSecurity] - Consiste básicamente en clases abstractas e interfaces que encapsulan conceptos de seguridad como certificados, claves, resumenes de mensajes y firmas digitales

```

#### UsuarioController
```sh
login  //  Login de los usuarios
me //  Devuelve los datos del usuario logeado
newUser  //  Registra un nuevo propietario
```

#### FollowerController

```sh
followUser // Método para seguir a un usuario por su user name
acceptFollow // Aceptar a un seguidor
declineFollow // Rechazar la peción de un seguidor
followListRequest // Lista de usuarios que quieren seguirte
```

#### PostController

```sh
create // Crear un nuevo post
findPostById // Encontrar un post por su id
findPublicPost // Encontrar podas las publicaciones públicas
findMyPost // Lista de todos mis publicaciones
deletePost // Borrar un post por su id
```

 Creado por: Cynthia Labrador
