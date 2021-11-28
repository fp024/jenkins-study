# 플러그인 만들기

> 원문: https://www.jenkins.io/doc/developer/tutorial/create/

* 스텝 1:  [플러그인 개발 준비하기](Preparing-for-Plugin-Development.md)

* **스탭 2: 플러그인 만들기**

* 스탭 3: [플러그인 빌드 및 실행하기](Build-and-Run-the-Plugin.md)

* 스탭 4: [플러그인 확장](Extend-the-Plugin.md)

[개발 환경](Preparing-for-Plugin-Development.md)을 준비한 후 다음 단계는 새 플러그인을 만드는 것입니다.

>Jenkins 업데이트 사이트에 플러그인을 게시하려는 경우 지금이 이미 유사한 작업을 수행하는 플러그인을 찾을 좋은 시간입니다. 자세한 내용은 [이 위키 페이지](https://wiki.jenkins.io/display/JENKINS/Before+starting+a+new+plugin)를 참조해보세요.



## Sample 플러그인 아키타입으로 프로젝트 레이아웃 만들기

명령 프롬프트를 열고 새로운 Jenkins 플러그인을 저장할 디렉터리로 이동한 후 다음 명령을 실행합니다.

```bash
mvn -U archetype:generate -Dfilter="io.jenkins.archetypes:"
```

이 명령을 사용하면 Jenkins와 관련된 여러 프로젝트 아키타입 중 하나를 생성할 수 있습니다. 이 튜토리얼에서는 `hello-world` 아키타입 버전 1.5를 사용할 것이므로 다음 내용대로 선택해보세요.

```bash
C:\git\jenkins-study\jenkins-plugin\plugin-tutorial\sample-plugins>mvn -U archetype:generate -Dfilter="io.jenkins.archetypes:"
[INFO] Scanning for projects...
Downloading from central: https://repo.maven.apache.org/maven2/org/codehaus/mojo/maven-metadata.xml
Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-metadata.xml
Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-metadata.xml (14 kB at 14 kB/s)
Downloaded from central: https://repo.maven.apache.org/maven2/org/codehaus/mojo/maven-metadata.xml (20 kB at 20 kB/s)
Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-archetype-plugin/maven-metadata.xml
Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-archetype-plugin/maven-metadata.xml (949 B at 3.4 kB/s)
[INFO]
[INFO] ------------------< org.apache.maven:standalone-pom >-------------------
[INFO] Building Maven Stub Project (No POM) 1
[INFO] --------------------------------[ pom ]---------------------------------
[INFO]
[INFO] >>> maven-archetype-plugin:3.2.0:generate (default-cli) > generate-sources @ standalone-pom >>>
[INFO]
[INFO] <<< maven-archetype-plugin:3.2.0:generate (default-cli) < generate-sources @ standalone-pom <<<
[INFO]
[INFO]
[INFO] --- maven-archetype-plugin:3.2.0:generate (default-cli) @ standalone-pom ---
[INFO] Generating project in Interactive mode
[INFO] No archetype defined. Using maven-archetype-quickstart (org.apache.maven.archetypes:maven-archetype-quickstart:1.0)
Choose archetype:
1: remote -> io.jenkins.archetypes:empty-plugin (Skeleton of a Jenkins plugin with a POM and an empty source tree.)
2: remote -> io.jenkins.archetypes:global-configuration-plugin (Skeleton of a Jenkins plugin with a POM and an example piece of global configuration.)
3: remote -> io.jenkins.archetypes:global-shared-library (Uses the Jenkins Pipeline Unit mock library to test the usage of a Global Shared Library)
4: remote -> io.jenkins.archetypes:hello-world-plugin (Skeleton of a Jenkins plugin with a POM and an example build step.)
5: remote -> io.jenkins.archetypes:scripted-pipeline (Uses the Jenkins Pipeline Unit mock library to test the logic inside a Pipeline script.)
Choose a number or apply filter (format: [groupId:]artifactId, case sensitive contains): : 4 # (1)
Choose io.jenkins.archetypes:hello-world-plugin version:
1: 1.1
2: 1.2
3: 1.3
4: 1.4
5: 1.5
6: 1.6
7: 1.7
8: 1.8
9: 1.9
10: 1.10
11: 1.11
Choose a number: 11: 5 # (2)
Downloading from central: https://repo.maven.apache.org/maven2/io/jenkins/archetypes/hello-world-plugin/1.5/hello-world-plugin-1.5.pom
Downloaded from central: https://repo.maven.apache.org/maven2/io/jenkins/archetypes/hello-world-plugin/1.5/hello-world-plugin-1.5.pom (737 B at 2.5 kB/s)
Downloading from central: https://repo.maven.apache.org/maven2/io/jenkins/archetypes/archetypes-parent/1.5/archetypes-parent-1.5.pom
Downloaded from central: https://repo.maven.apache.org/maven2/io/jenkins/archetypes/archetypes-parent/1.5/archetypes-parent-1.5.pom (4.6 kB at 16 kB/s)
Downloading from central: https://repo.maven.apache.org/maven2/io/jenkins/archetypes/hello-world-plugin/1.5/hello-world-plugin-1.5.jar
Downloaded from central: https://repo.maven.apache.org/maven2/io/jenkins/archetypes/hello-world-plugin/1.5/hello-world-plugin-1.5.jar (17 kB at 47 kB/s)
[INFO] Using property: groupId = unused # (3)
Define value for property 'artifactId': demo # (4)
Define value for property 'version' 1.0-SNAPSHOT: :  # (5)
[INFO] Using property: package = io.jenkins.plugins.sample
Confirm properties configuration:
groupId: unused
artifactId: demo
version: 1.0-SNAPSHOT
package: io.jenkins.plugins.sample
 Y: : y # (6)
...
```

1. `hello-world-plugin` 아키타입에 대한 번호 **4**를 입력합니다.
2. 이 튜토리얼은 `hello-world-plugin` 아키타입 버전 1.5를 기반으로 하므로 **5**를 입력하여 선택합니다.
3. `groupId`는 모든 프로젝트에서 프로젝트를 고유하게 식별합니다. 그룹 ID는 Java의 패키지 이름 규칙을 따라야 합니다. 즉, 역 도메인 이름으로 시작합니다. 예: `io.jenkins.plugins`
4. artifactId는 필수이며 Jenkins에서 플러그인을 고유하게 식별합니다. 이 maven 프로젝트에서 생성되는 기본 아티팩트의 고유한 기본 이름입니다. 이 플러그인 튜토리얼은 `demo`라는 이름을 사용합니다(볼드체로 강조 표시된 사용자 입력). 플러그인을 게시하려면 이름이 이미 사용되지 않았는지, 선택한 이름이 미래에 대비할 수 있는지 확인하세요. 첫 번째 릴리스를 게시한 후에는 artifactId를 변경할 수 없습니다. 이 ID에 `jenkins` 또는 `plugin`이라는 단어를 사용하지 말고 이것이 어떤 종류의 Jenkins 플러그인인지 설명하는 단어만 사용하세요.
5. 여기에서 다른 버전 번호를 선택할 필요가 없습니다. 이것은 버전 1.0의 개발 버전(`SNAPSHOT`으로 나타냄)입니다. [Maven 버전 번호](https://stackoverflow.com/q/5901378)에 대해 자세히 알아보세요.
6. 모든 값을 입력하면 Maven이 입력 했던 내용들을 다시 표시합니다. 선택 사항을 검토하고 확인합니다.

아래 명령은 플러그인의 artifactId(이 경우 demo)와 동일한 이름을 가진 디렉토리를 생성하고 작동하는 플러그인의 기본 구조를 추가합니다. 플러그인을 빌드할 수 있는지 확인해봅시다.

```bash
C:\git\jenkins-study\jenkins-plugin\plugin-tutorial\sample-plugins>rename demo demo-plugin # (1)
C:\git\jenkins-study\jenkins-plugin\plugin-tutorial\sample-plugins>cd demo-plugin
C:\git\jenkins-study\jenkins-plugin\plugin-tutorial\sample-plugins\demo-plugin>mvn verify
```

1. Maven은 플러그인에 대해 선택한 것과 동일한 이름으로 디렉토리에 프로젝트 구조를 생성했습니다. GitHub 조직 `@jenkinsci`에서 사용되는 기존 리포지토리 이름과 일치하도록 디렉터리 이름을 변경합니다.

Maven은 몇 가지 추가 의존성을 다운로드한 후, 다음과 같이 표시될 때까지 정적 분석(Spotbugs) 및 테스트를 포함하여 구성된 빌드 라이프 사이클을 거칩니다.

```
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  06:13 min
[INFO] Finished at: 2021-11-28T23:59:15+09:00
[INFO] ------------------------------------------------------------------------
```

> 플러그인 빌드와 관련된 내용에 대해 자세히 알아보려면 [플러그인 빌드 프로세스](https://www.jenkins.io/doc/developer/plugin-development/build-process)를 참조해보세요.

위의 내용이 진행하고 있는 출력 내용과 다른가요? 그러면 아래 문제 해결 섹션을 참조해보세요.

다음으로는 [플러그인을 실행](Build-and-Run-the-Plugin.md)하고 어떤 기능을 하는지 살펴보겠습니다.



## 문제 해결

> 작동하지 않는 것이 있습니까? 채팅이나 [jenkinsci-dev 메일링 리스트](https://www.jenkins.io/mailing-lists)에서 도움을 요청해보세요.
