# MyPub Service

MyPub web backend to provide user-centric services.

This is intended to be held by a trustworthy decentralized organization.

## Configuration

### Global Properties

> GlobalProperties.java

- `workspace` workspace location
- `encryptor` encryptor name

### Encryptor

Compile and distribute [MyPub Encryptor](https://github.com/yepengding/MyPubEncryptor) into workspace.

## Commands

> Require encryptor in workspace

### Compile

```
mvn package
```

### Run

```
java -jar *.jar
```

## API

> http://localhost:8080/swagger-ui/

**Contact information:**  
Yepeng Ding  
https://yepengding.github.io/

**License:** [MIT License](https://github.com/yepengding/IPFSServerAPISimulator/blob/main/LICENSE)

### /cipher/encrypt

#### POST

##### Summary:

encrypt

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| file | body | file | Yes | binary |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [CommonResult«Encrypted»](#CommonResult«Encrypted») |

### /file/{name}

#### GET

##### Summary:

getFileByCID

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| name | path | name | Yes | string |

##### Responses

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [Resource](#Resource) |
| 403 | Forbidden |  |
| 500 | Internal Error |  |

### Models

#### CommonResult«Encrypted»

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| code | long |  | No |
| data | [Encrypted](#Encrypted) |  | No |
| message | string |  | No |

#### Encrypted

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| decryptor | string |  | No |
| file | string |  | No |
| key | string |  | No |

#### InputStream

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| InputStream | object |  |  |

#### Resource

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| description | string |  | No |
| file | file |  | No |
| filename | string |  | No |
| inputStream | [InputStream](#InputStream) |  | No |
| open | boolean |  | No |
| readable | boolean |  | No |
| uri | string (uri) |  | No |
| url | string (url) |  | No |

## MyPub Components

* [MyPub Front + Chain Linker](https://github.com/yepengding/MyPub/tree/main/ui)
* [MyPub Contract](https://github.com/yepengding/MyPub/tree/main/contracts)
* MyPub Cipher
    - [Encryptor](https://github.com/yepengding/MyPubEncryptor)
    - [Decryptor](https://github.com/yepengding/MyPubDecryptor)
* [MyPub Service](https://github.com/yepengding/MyPubService)

## Reference

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.4/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.4/maven-plugin/reference/html/#build-image)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.5.4/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.5.4/reference/htmlsingle/#boot-features-developing-web-applications)
* [Thymeleaf](https://docs.spring.io/spring-boot/docs/2.5.4/reference/htmlsingle/#boot-features-spring-mvc-template-engines)
* [swagger](https://swagger.io/)
* [guava](https://github.com/google/guava)