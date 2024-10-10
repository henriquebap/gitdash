package br.com.fiap.gitdash.github;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
public class GitHubController {

    private static final Logger logger = LoggerFactory.getLogger(GitHubController.class);

    private final GitHubService gitHubService;

    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/")
    public String getUserInfo(Model model, @RegisteredOAuth2AuthorizedClient("github") OAuth2AuthorizedClient authorizedClient) {
        if (authorizedClient == null) {
            logger.error("OAuth2AuthorizedClient is null. User might not be authenticated.");
            return "error"; // Pode ser uma página de erro personalizada
        }
    
        String tokenValue = authorizedClient.getAccessToken().getTokenValue();
        logger.info("Access token obtained successfully.");
    
        // Obter repositórios do usuário utilizando o token
        List<RepositoryInfo> repos = gitHubService.getUserRepositories(tokenValue);
        model.addAttribute("repos", repos);
    
        // Obter e adicionar o nome, avatar e URL do perfil do usuário ao modelo
        String userName = gitHubService.getUserName(tokenValue);
        String avatarUrl = gitHubService.getUserAvatarUrl(tokenValue); // Novo método para obter URL do avatar
        String htmlUrl = gitHubService.getUserHtmlUrl(tokenValue); // Novo método para obter URL do perfil do GitHub
    
        model.addAttribute("name", userName);
        model.addAttribute("avatarUrl", avatarUrl);
        model.addAttribute("htmlUrl", htmlUrl);
    
        return "user";
    }
}
