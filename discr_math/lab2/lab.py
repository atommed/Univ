import math

class GraphMatrix:
    def __init__(self, M, N):
        self.M = M
        self.N = N
        self.__data = [[0] * N] * M
        
    def __getitem__(self, index):
        return self.__data[index]
    
    def __iter__(self):        
        yield from self.__data
        
    def __str__(self):
        res = ""
        res += "M: %d N: %d" % (self.M, self.N)
        for row in self:
            res+="\n"
            res+=str(row)        
        return res

    @staticmethod
    def loadFromFile(name):
        def rowFromString(s):
            row = []
            for snum in s.split(' '):
                if snum =='~':
                    row.append(math.inf)
                else:
                    num = None
                    try:
                        num = int(snum)
                    except ValueError:
                        num = float(snum)
                    row.append(num)
            return row
            
        rows = []
        f = open(name,'r')
        for line in f:
            rows.append(rowFromString(line.rstrip()))
        f.close()
        M = len(rows)
        N = len(rows[0])
        for row in rows:
            if len(row) != N:
                raise ValueError("Different rows len: %d and %d" % (N,len(row)))
        res = GraphMatrix(M,N)
        res.__data = rows
        return res


def find_min_path(D, start):
    """
    Finds and prints minimal paths from start vertex in G
    """

    def find_min_distance(M, visited):
        min = math.inf
        min_index = None
        for v in range(len(M)):
            if not visited[v] and M[v] <= min:
                min = M[v]
                min_index = v
        return v
    
    assert D.M == D.N
    dism = D.M
    #Initialization
    M = [math.inf] * dism
    M[start] = 0
    visited = [False] * dism
    visited[start] = True
    #Steps
    for i in range(dism):
        u = find_min_distance(M,visited)
        visited[u] = True
        for v in range(dism):
            u = find_min_distance(M,visited)
            if(not visited[v] and M[v] != math.inf
               and M[u]+D[u][v] < M[v]):
                print("Something")
                M[v] = m[u] + D[u][v]
            
    return M
            
a = GraphMatrix.loadFromFile('matrix.txt')

print(a)
print("Min path lengths:")
print(find_min_path(a, 0))
