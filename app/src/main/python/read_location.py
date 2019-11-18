import pickle
from sklearn import tree
def test():

	#decision_tree_pkl_filename = 'decision_tree_classifier_20170212.pkl'


    #return 'hello word'
	# Loading the saved decision tree model pickle
	#decision_tree_model_pkl = open(decision_tree_pkl_filename, 'rb')
	#decision_tree_model = pickle.load(decision_tree_model_pkl)

	#clfX = decision_tree_model.predict([[b1, b2, b3]])
	#return clfX
    #X = [[-62, -61], [-74, -64]]
    X = [[-62, -61  , -65], [-74, -64, -72], [-69, -62, -61]]
    #X = [[0, 0], [1, 1]]
    Y = [[12, 5],[12,5],[12,5]]
    clf = tree.DecisionTreeClassifier()

    clf = clf.fit(X, Y)
    clfX = clf.predict([[-57, 60, -70],[-56, -60, -66]])
    #print(clfX)
    return clfX
