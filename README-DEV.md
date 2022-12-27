# Github App development and workflow Guidelines

## General development checklist

- Find ticket and assigne to yourself
- Set ticket state to `In Progress`
- Create feature branch from development branch
- The feature branch naming follows the pattern `feature/GITHUBAPP-ticketnumber_description`
- Create a WIP-MR assigned to yourself, title of MR should start with `#ticketnumber`
- Implement the feature - every commit message start with `GITHUBAPP-ticketnumber:`
- If the feature is deemed complete, remove WIP, assign teammate as reviewer for MR and set ticket state to `In review` 
- A feature can only be merged to develop after teammates approves 
- After approve, delete the source branch and squash commits on merging, close ticket
- Pull remote dev branch changes to local dev branch and repeat the process

## Before requesting a Merge Request code review

- Check if title of MR is setup correct 
- Add a description to MR, applying the appropriate template
