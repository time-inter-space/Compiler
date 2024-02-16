#define bool _Bool

int scanf(const char *pattern, ...);
int sscanf(const char *src, const char *pattern, ...);
int printf(const char *pattern, ...);
int sprintf(char *dest, const char *pattern, ...);
unsigned strlen(const char *str);
int strcmp(const char *s1, const char *s2);
void *memcpy(void *dest, const void *src, unsigned n);
void *malloc(unsigned n);

void print(char *str) {
    printf("%s", str);
}
void println(char *str) {
    printf("%s\n", str);
}
void printInt(int n) {
    printf("%d", n);
}
void printlnInt(int n) {
    printf("%d\n", n);
}
char *getString() {
    char *ret = malloc(4096);
    scanf("%s", ret);
    return ret;
}
int getInt() {
    int ret;
    scanf("%d", &ret);
    return ret;
}
char *toString(int i) {
    char *ret = malloc(16);
    sprintf(ret, "%d", i);
    return ret;
}
char *builtin_string_add(char *s1, char *s2) {
    int len1 = strlen(s1), len2 = strlen(s2);
    char *ret = malloc(len1 + len2 + 1);
    memcpy(ret, s1, len1);
    memcpy(ret + len1, s2, len2);
    ret[len1 + len2] = 0;
    return ret;
}
bool builtin_string_eq(char *s1, char *s2) {
    return strcmp(s1, s2) == 0;
}
bool builtin_string_ne(char *s1, char *s2) {
    return strcmp(s1, s2) != 0;
}
bool builtin_string_lt(char *s1, char *s2) {
    return strcmp(s1, s2) < 0;
}
bool builtin_string_le(char *s1, char *s2) {
    return strcmp(s1, s2) <= 0;
}
bool builtin_string_gt(char *s1, char *s2) {
    return strcmp(s1, s2) > 0;
}
bool builtin_string_ge(char *s1, char *s2) {
    return strcmp(s1, s2) >= 0;
}
int builtin_string_length(char *s) {
    return strlen(s);
}
char *builtin_string_substring(char *s, int left, int right) {
    char *ret = malloc(right - left + 1);
    memcpy(ret, s + left, right - left);
    ret[right - left + 1] = 0;
    return ret;
}
int builtin_string_parseInt(char *s) {
    int ret;
    sscanf(s, "%d", &ret);
    return ret;
}
int builtin_string_ord(char *s, int pos) {
    return s[pos];
}
int builtin_array_size(void *arr) {
    return ((int*)arr)[-1];
}
void *builtin_new_array(int n, int sz) {
    int *ret = malloc(n * sz + 4);
    ret[0] = n;
    return ret + 1;
}
void *builtin_new_var(int sz) {
    return malloc(sz);
}