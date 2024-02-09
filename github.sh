message=$1
git pull
echo "Pushing results to git..."
git add .
git commit -m "github.sh:$message"
git push  amiri amiri
