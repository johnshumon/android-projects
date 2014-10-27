android-projects
================

This repository contains all android projects I have developed as either hobby project or assignment.


# Some useful commands that will regarding different aspects of git operations. Trying to put together as many as possible.
===========================================================================================================================


## Remove directory from remote repository after adding them to .gitignore.
===========================================================================
The rules in your .gitignore file only apply to untracked files. Since the files under that directory were already committed in your repository, you have to unstage them, create a commit, and push that to GitHub:

git rm -r --cached some-directory
git commit -m 'Remove the now ignored directory "some-directory"'
git push origin master
[original post](http://stackoverflow.com/questions/7927230/remove-directory-from-remote-repository-after-adding-them-to-gitignore)



