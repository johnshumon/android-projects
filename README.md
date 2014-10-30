# ANDROID-PROJECTS
================

This repository contains all android projects I have developed as either hobby project or assignment.





#### Some useful commands that will help regarding different aspects of git operations. Trying to put together as many as possible.
===================================================================================================================


#### Remove directory from remote repository after adding them to .gitignore.
===========================================================================
The rules in your .gitignore file only apply to untracked files. Since the files under that directory were already committed in your repository, you have to unstage them, create a commit, and push that to GitHub:

git rm -r --cached some-directory

git commit -m 'Remove the now ignored directory "some-directory"'

git push origin master

[original post](http://stackoverflow.com/questions/7927230/remove-directory-from-remote-repository-after-adding-them-to-gitignore)


#### Download a single folder or directory from a GitHub repo
===========================================================================
svn checkout https://github.com/syeed007/Android-Projects/trunk/ModernArtUI
tree/master is being replaced with trunk.

[original post](http://stackoverflow.com/questions/7106012/download-a-single-folder-or-directory-from-a-github-repo/18194523#18194523)




