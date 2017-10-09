#include <stdio.h>
#include <stdlib.h>


int main(void)
{
    FILE *file = fopen("card.raw", "r");
    if (file == NULL)
    {
        fprintf(stderr, "Could not open %s.\n", "card.raw");
        return 2;
    }
    
    unsigned char buffer[512];
    FILE* picture = NULL;
    int counter = 0;
    int found = 0;
    
    while(fread(buffer, 512, 1, file)){
        if (buffer[0] == 0xff && buffer[1] == 0xd8 && buffer[2] == 0xff && (buffer[3] & 0xe0) == 0xe0){
            if(found==1){
                // We found the start of a new pic so close out current picture
                fclose(picture);
            }else{
                // jpg discovered and now we have the green light to write
                found=1;
            }
            char filename[8];
            sprintf(filename, "%03d.jpg", counter);
            picture = fopen(filename, "w");
            counter++;
        }
        if(found==1){
            fwrite(buffer, 512, 1, picture);
        }
    }
    fclose(picture);
    fclose(file);
}