## Master

Never push directly to master. Master is always deployable. Master will be updated with every iteration.

## Iteration Branch

This will be our main develop branch. As a general rule, never push directly to the iteration branch. Merge as often as possible.

##  Per Developer Branches

Create a branch for your work, which you will be merging with the iteration branch.

## Merging

Do not merge into the iteration branch from the command-line. Always create a merge request into the iteration branch and wait for 2 thumbs-up.

## Naming

Always be clear as to what the branch is for in the name. Names should, of course, following the lowercase-dashes style.

## Squashing

Squash commits from feature/story branches into the iteration branch.

## Commit Message

Follow the standards in https://chris.beams.io/posts/git-commit/

## Unit Tests

Unit tests should always be in place before merging into the iteration branch. As a rule, do not merge broken code. Unit tests will help keep this rule.


## Process

1. Create your branch

    `git checkout -b branch-name`

2. Do your work and commit (as many times as you like)

    .....work.....

    `git add *`
    
    `git commit`

3. Merge the remote branch into your local branch
    
    `git pull`

    3.1 Resolve any conflicts (you may have to commit again after this)
    
4. Merge the parent branch into your local branch 



    `git checkout iteration-1`
    
    `git pull`
    
    `git checkout my-branch-name`
    
    `git merge iteration-1`

    4.1 Resolve any conflicts (you may have to commit again after this)

5. Push to remote (as many times as you like)

    `git push`

6. Create merge request on GitLab
    - generally, select to squash commits
    - if you are not doing any more work on this branch, then select to delete it once merged

7. Wait for code review

8. Make changes according to review

9. Wait for 2 approvals (7 & 8 may be repeated until satisfied with your commit)

10. You (or anyone) can merge the branch once 2 approvals (thumbs-up) are obtained


